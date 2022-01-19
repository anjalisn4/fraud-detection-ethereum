from flask import Flask, request, jsonify, render_template

app = Flask(__name__)


@app.route('/')
def home():
    return render_template('index.html')


@app.route('/classify',methods=['POST'])
def classify():
    int_features = [(x) for x in request.form.values()]
    return render_template('index.html', prediction_text='The account is classified as {}'.format("Malicious"))

if __name__ == '__main__':
    app.run(port=7000, debug=True)
