from flask import Flask, request
from flask_cors import CORS
from waitress import serve
import numpy as np
import face_recognition

app = Flask(__name__)
CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'
app.config['UPLOAD_FOLDER'] = "static"

@app.route('/join', methods=['POST'])
def checkFace():
    image = request.files['image']
    studentCode = request.form['studentCode']
    unknown_image = face_recognition.load_image_file(image)

    unknown_encoding = face_recognition.face_encodings(unknown_image)[0]
    student_encoding = np.load(studentCode + '.npy')
    result = face_recognition.compare_faces([student_encoding], unknown_encoding)
    return {'result': 1 if result.pop() else 0}

if __name__ == '__main__':
    serve(app, host='0.0.0.0', port='5000')