from flask import Flask, request
import openai


app = Flask(__name__)

@app.route('/gptTest', methods=['POST'])
def predict():
    test_json = request.get_json()

    import openai
    text = 'aaa'
    openai.api_key = "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"
    response = openai.Completion.create(model="text-davinci-003", prompt=text, temperature=0.6, max_tokens=4000)

    for i in response.choices:
        print(i.text)

    df_raw['valor'] = 0
    for i in range(len(results)):
        if results[i] == 'yes':
            df_raw['valor'] = 1

    return df_raw.to_json(orient='records')

if __name__ == '__main__':
    port = os.environ.get('PORT', 5000)
    app.run(host='0.0.0.0', port=port)