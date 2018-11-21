import base64
import uuid
from datetime import datetime

from flask import Flask, jsonify
from flask_sqlalchemy import SQLAlchemy
from PIL import Image

app = Flask(__name__)

app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///site.db'

db = SQLAlchemy(app)
class Img(db.Model):
    id = db.Column(db.Integer, primary_key = True)
    dateposted =db.Column(db.DateTime, nullable = False, default = datetime.utcnow)
    isfood = db.Column(db.Boolean, nullable = False)
    confidence  = db.Column(db.Integer)
    image = db.Column(db.String(20), nullable = False)

@app.route('/imglist', methods = ['GET'])
def get_image_list():
    imgs = Img.query.all()
    
    output = []
    for img in imgs:
        img_data = {}
        img_data['img_id'] = img.id
        img_data['dateposted'] = img.dateposted
        img_data['isFood'] = img.isfood
        img_data['confidence'] = img.confidence
        img_data['image'] = img.image

        output.append(img_data)


    return jsonify({'imgs' : output})

@app.route('/newimage', methods = ['POST', 'GET'])
def add_image():
    data = request.get_json()

    
    new_image = Img(img_id = str(uuid.uuid4()), dateposted = data['dateposted'], 
    isfood = data['isFood'], confidence = data['confidence'], image = data['image'])


@app.route('/findfood/<img_id>', methods = ['POST', 'GET'])
def run_findfood():
    return 'food'
    
if __name__ == '__main__':
    app.run(debug=True)
