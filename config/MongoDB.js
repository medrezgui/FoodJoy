const mongoose = require('mongoose');
require('dotenv').config(); // si vous utilisez .env

const mongoURI = process.env.MONGO_URI || "mongodb://127.0.0.1:27017/maBase";

mongoose.connect(mongoURI, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})
.then(() => console.log("Connexion à MongoDB réussie"))
.catch((err) => console.log("Erreur de connexion à MongoDB :", err));

module.exports = mongoose;
