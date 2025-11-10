const Recompense = require('../models/Recompense');

class RecompenseService {
  async creerRecompense(data) {
    const recompense = new Recompense(data);
    return await recompense.save();
  }

  async obtenirToutes() {
    return await Recompense.find();
  }

  async obtenirParId(id) {
    return await Recompense.findById(id);
  }

  async mettreAJour(id, data) {
    return await Recompense.findByIdAndUpdate(id, data, { new: true });
  }

  async supprimer(id) {
    return await Recompense.findByIdAndDelete(id);
  }

  async obtenirParUserId(userId) {
    return await Recompense.find({ userId });
  }
  
    // ✅ Supprimer toutes les récompenses
  async supprimerTous() {
    return await Recompense.deleteMany({});
  }

}

module.exports = new RecompenseService();
