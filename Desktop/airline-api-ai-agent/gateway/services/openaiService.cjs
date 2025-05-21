require("dotenv").config();
const { OpenAI } = require("openai");
dotenv.config();

const openai = new OpenAI({
  apiKey: process.env.OPENAI_API_KEY,
});

export async function callOpenAI(message) {
  const chatCompletion = await openai.chat.completions.create({
    messages: [{ role: 'user', content: message }],
    model: 'gpt-3.5-turbo',
  });

  return chatCompletion.choices[0].message.content;
}
