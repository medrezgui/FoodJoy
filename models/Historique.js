const mongoose = require('mongoose');

const historiqueSchema = new mongoose.Schema({
    userId: String,           // userId Keycloak
    campagneId: String,       // ID de la campagne
    dateApplication: { type: Date, default: Date.now },
    remiseAppliquee: Number,  // montant ou pourcentage
    commandeId: String        // ID de la commande si applicable
});

module.exports = mongoose.model('Historique', historiqueSchema);
