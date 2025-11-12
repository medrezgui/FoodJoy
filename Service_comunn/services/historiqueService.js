const Historique = require('../models/Historique');

class HistoriqueService {
  async creerHistorique(data) {
    const historique = new Historique(data);
    return await historique.save();
  }

  async obtenirTous() {
    return await Historique.find();
  }

  async obtenirParId(id) {
    return await Historique.findById(id);
  }

  async obtenirParUserId(userId) {
    return await Historique.find({ userId });
  }

  async supprimer(id) {
    return await Historique.findByIdAndDelete(id);
  }
    // ✅ Supprimer tous les historiques
  async supprimerTous() {
    return await Historique.deleteMany({});
  }
}

module.exports = new HistoriqueService();
