from flask import Flask, request, jsonify, render_template

import logic

app = Flask(__name__)


@app.route('/')
def home():
    return render_template('index.html')

@app.route('/classify', methods=['POST'])
def classify():
    address = [(x) for x in request.form.values()]
    pred = 'null'
    prediction = logic.pred(address[0])
    if prediction[0] == 1:
        pred = "Malicious"
    elif prediction[0] == 0:
        pred = 'Non-Malicious'
    else:
        pred = 'Invalid'

    return render_template('index.html', prediction_text='The account type is {} and classified as {}'.format(prediction[1], pred))


if __name__ == '__main__':
    app.run(port=7000, debug=True)
