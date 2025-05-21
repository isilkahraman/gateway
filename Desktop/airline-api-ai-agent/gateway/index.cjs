const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const axios = require("axios");

const app = express();
app.use(cors());
app.use(bodyParser.json());

const { getIntentFromMessage } = require("./intentService.cjs");

const SPRING_API = "http://localhost:8081";
const AUTH_TOKEN = "Bearer <JWT_TOKEN_HERE>";

// Simple answer for main root (GET /)
app.get("/", (req, res) => {
  res.send("Gateway API is running.");
});

// Chat endpoint
app.post("/chat", async (req, res) => {
  const userInput = req.body.message;

  try {
    const intentData = await getIntentFromMessage(userInput);
    const intent = intentData.intent;
    const params = intentData.params;

    if (intent === "queryFlight") {
      const result = await axios.get(`${SPRING_API}/api/v1/flights`, {
        headers: { Authorization: AUTH_TOKEN },
        params: {
          airportFrom: params.airportFrom,
          airportTo: params.airportTo,
          dateFrom: params.dateFrom + "T00:00:00",
          dateTo: params.dateTo + "T23:59:59",
          sortByDate: true
        }
      });

      res.json(result.data);
    } else {
      res.status(400).json({ message: "Intent not supported" });
    }
  } catch (error) {
    console.error("Error handling /chat:", error.message);
    res.status(500).json({ error: "Internal server error" });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Gateway listening on port ${PORT}`);
});
