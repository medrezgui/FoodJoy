const Campagne = require('../models/Campagne');

class CampagneService {
  async creerCampagne(data) {
    const campagne = new Campagne(data);
    return await campagne.save();
  }

  async obtenirToutes() {
    return await Campagne.find();
  }

  async obtenirParId(id) {
    return await Campagne.findById(id);
  }

  async mettreAJour(id, data) {
    return await Campagne.findByIdAndUpdate(id, data, { new: true });
  }

  async supprimer(id) {
    return await Campagne.findByIdAndDelete(id);
  }

  async obtenirActives() {
    return await Campagne.find({ actif: true });
  }

  async supprimerToutes() {
    return await Campagne.deleteMany({});
  }
///metier avancee 
async desactiverCampagnesExpirees() {
    const now = new Date();
    const result = await Campagne.updateMany(
      { dateFin: { $lt: now }, actif: true },
      { $set: { actif: false } }
    );
    return result.modifiedCount;
  }
}


module.exports = new CampagneService();
