from flask import Flask, request
import openai
import os

app = Flask(__name__)


@app.route('/gptTest', methods=['POST'])
def gpt_tests():
    test_json = request.get_json()
    url = test_json['url']
    typeReq = test_json['typeReq']
    body = test_json['body']
    params = test_json['params']
    headers = test_json['headers']
    openai.api_key = test_json['apyKey']

    text = 'Teste a API com a url ' + url + ' com o tipo de requisição ' + typeReq

    if body != '':
        text += ' com o body ' + body
    if params != '':
        text += ' com os parametros ' + params
    if headers != '':
        text += ' com os headers ' + headers

    response = openai.Completion.create(model="text-davinci-003", prompt=text, temperature=0.6, max_tokens=4000)

    for i in response.choices:
        response[i] = i.text

    return response.to_json(orient='records')


if __name__ == '__main__':
    port = os.environ.get('PORT', 5000)
    app.run(host='0.0.0.0', port=port)
