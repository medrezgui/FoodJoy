const mongoose = require('mongoose');

const recompenseSchema = new mongoose.Schema({
  userId: String,  // ID utilisateur Keycloak
  type: { 
    type: String, 
    enum: ['Bon', 'Remise', 'Cadeau'], 
    required: true 
  },
  valeur: Number,  // montant ou pourcentage
  dateObtention: { type: Date, default: Date.now },
  utilise: { type: Boolean, default: false },
  historiqueId: String  // Optionnel : relier la récompense à un historique de campagne
});

module.exports = mongoose.model('Recompense', recompenseSchema);
