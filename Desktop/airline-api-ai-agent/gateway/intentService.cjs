require("dotenv").config();
const { OpenAI } = require("openai");


const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function getIntentFromMessage(message) {
  const prompt = `
You are a flight ticket AI assistant. Extract intent and parameters from user message as JSON.

Supported intents: queryFlight, buyTicket, checkIn
Time default: 2025-06-01

Examples:
Message: "Are there flights from Ankara to Istanbul?"
Response: { "intent": "queryFlight", "params": { "airportFrom": "Ankara", "airportTo": "Istanbul", "dateFrom": "2025-06-01", "dateTo": "2025-06-01" } }

Message: "Buy ticket for flight TK345"
Response: { "intent": "buyTicket", "params": { "flightNumber": "TK345", "passengerName": "AI Agent" } }

Message: "Check in for seat 10A for ticket 1"
Response: { "intent": "checkIn", "params": { "ticketNumber": 1, "seatNumber": "10A" } }

Now process the message:
Message: "${message}"
Answer:
`;

  const completion = await openai.chat.completions.create({
    model: "gpt-3.5-turbo",
    messages: [{ role: "user", content: prompt }],
    temperature: 0.1
  });

  const raw = completion.choices[0].message.content;
  return JSON.parse(raw);
}

module.exports = { getIntentFromMessage };

