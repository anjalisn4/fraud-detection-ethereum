from flask import Flask, request, jsonify, render_template

import logic

app = Flask(__name__)


@app.route('/')
def home():
    dict = {}
    return render_template('index.html',result = dict)

@app.route('/classify', methods=['POST'])
def classify():
    address = [(x) for x in request.form.values()]
    response = logic.pred(address[0])
    return render_template('index.html',result = response)


if __name__ == '__main__':
    app.run(port=7000, debug=True)
