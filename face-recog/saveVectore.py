import face_recognition
import numpy as np
    
known_image = face_recognition.load_image_file("unknown.jpg")

huy_encoding = face_recognition.face_encodings(known_image)
np.save('1234.npy', huy_encoding)
student_encoding = np.load('1234.npy')
print(known_image)
print(huy_encoding)