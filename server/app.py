#!flask/Scripts/python
from __future__ import print_function
from flask import Flask, request, jsonify, abort, make_response, url_for
from PIL import Image
import numpy as np
import tensorflow as tf
import os
import io
import sys
import subprocess
import pickle

app = Flask(__name__)

## collection of images tested by AI
images = [
    {
		'id': 1,
		'name': u'',
		'byte': u'',
		'isFavorite': False,
		'isFood': False,
		'confidence': u'[]'
    }
]

## hold tensorflow variables as global
sess = None
x_input = None
keep_prob = None
class_scores = None

## method to initialize tensorflow graph and variables
@app.route('/api/initialize', methods=['GET'])
def initialize():
	###### Initialization code - we only need to run this once and keep in memory.
	global sess
	sess = tf.Session()
	saver = tf.train.import_meta_graph('saved_model/model_epoch5.ckpt.meta')
	saver.restore(sess, tf.train.latest_checkpoint('saved_model/'))
	graph = tf.get_default_graph()
	global x_input
	x_input = graph.get_tensor_by_name('Input_xn/Placeholder:0')
	global keep_prob
	keep_prob = graph.get_tensor_by_name('Placeholder:0')
	global class_scores
	class_scores = graph.get_tensor_by_name("fc8/fc8:0")
	return "success"

## method to test images
def test():
	## will open image saves
	image = Image.open("testImg.jpg").convert('RGB')
	image = image.resize((227, 227), Image.BILINEAR)
	img_tensor = [np.asarray(image, dtype=np.float32)]
	toReturn = 'looking for food in testImg.jpg\n'

	#Run the image in the model.
	scores = sess.run(class_scores, {x_input: img_tensor, keep_prob: 1.})
	toReturn = toReturn + str(scores) + '\n'
	# if np.argmax = 0; then the first class_score was higher, e.g., the model sees food.
	# if np.argmax = 1; then the second class_score was higher, e.g., the model does not see food.
	if np.argmax(scores) == 1:
		toReturn = toReturn + "No food here... :( "
	else:
		toReturn = toReturn + "Oh yes... I see food! :D"
	return toReturn

## method to save image database in file
@app.route('/api/close', methods=['GET'])
def on_close():
	with open("database", 'wb') as f:
		pickle.dump(images, f)
	return jsonify({'images': images})

## method to retrieve images database from file
@app.route('/api/open', methods=['GET'])
def on_open():
	with open("database", 'rb') as f:
		global images
		images = pickle.load(f)
	return jsonify({'images': images})

## error handling
@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)

## tests and posts image to database
@app.route('/api/images', methods=['POST'])
def create_image():
	## aborts if wrong format or missing byte string
	if not request.json or not 'byte' in request.json:
		abort(400)

	## saves image sent into temp image
	fh = open("testImg.jpg", "wb")
	fh.write(request.json['byte'].decode('base64'))
	fh.close()

	## tests image
	cmdString = test()
	## receives AI response
	tokens = cmdString.splitlines()
	
	## parses response and saves image to database
	if "I see food" in cmdString:
		image = {
			'id': images[-1]['id'] + 1,
			'name': u'',
			'byte': request.json['byte'],
			'isFavorite': False,
			'isFood': True,
			'confidence': tokens[1]
		}
	else:
		image = {
			'id': images[-1]['id'] + 1,
			'name': u'',
			'byte': request.json['byte'],
			'isFavorite': False,
			'isFood': False,
			'confidence': tokens[1]
		}
	images.append(image)
	return jsonify({'image': image}), 201

## updates record in database
@app.route('/api/images/<int:image_id>', methods=['PUT'])
def update_image(image_id):
	## given image id, will update image information
	image = [image for image in images if image['id'] == image_id]
	if len(image) == 0:
		abort(404)
	if not request.json:
		abort(400)
	task[0]['name'] = request.json.get('name', task[0]['name'])
	task[0]['isFavorite'] = request.json.get('isFavorite', task[0]['isFavorite'])
	return jsonify({'task': task[0]})

## will return specific image given image id
@app.route('/api/images/<int:image_id>', methods=['GET'])
def get_image(image_id):
    image = [image for image in images if image['id'] == image_id]
    if len(image) == 0:
        abort(404)
    return jsonify({'image': image[0]})

## deleted image from database given image id
@app.route('/api/images/<int:image_id>', methods=['DELETE'])
def delete_image(image_id):
    image = [image for image in images if image['id'] == image_id]
    if len(image) == 0:
        abort(404)
    images.remove(image[0])
    return jsonify({'result': True})

## returns all images
@app.route('/api/images', methods=['GET'])
def get_images():
    return jsonify({'images': images})

## sets host
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
