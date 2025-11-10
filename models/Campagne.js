const mongoose = require('mongoose');

const campagneSchema = new mongoose.Schema({
    nom: String,
    description: String,
    type: { type: String, enum: ['Fidélité', 'Promotion', 'Evenement'], required: true },
    dateDebut: Date,
    dateFin: Date,
    remise: Number,
    utilisateursCibles: [String],  // userId Keycloak
    actif: { type: Boolean, default: true }
});

module.exports = mongoose.model('Campagne', campagneSchema);
