const campagneService = require('../services/campagneService');
const Campagne = require('../models/Campagne'); // <-- c'est obligatoire
const cron = require('node-cron');
// ✅ Créer une campagne
exports.creerCampagne = async (req, res, next) => {
  try {
    // Crée une instance de Campagne avec les données reçues
    const campagne = new Campagne(req.body);

    // Sauvegarde dans la base de données (Mongoose valide types et champs requis)
    const savedCampagne = await campagne.save();

    res.status(201).json(savedCampagne);
  } catch (err) {
    // Si erreur de validation ou autre, on passe au middleware errorHandler
    next(err);
  }
};

// ✅ Obtenir toutes les campagnes
exports.obtenirToutes = async (req, res) => {
  try {
    const campagnes = await campagneService.obtenirToutes();
    res.status(200).json(campagnes);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Obtenir une campagne par ID
exports.obtenirParId = async (req, res) => {
  try {
    const campagne = await campagneService.obtenirParId(req.params.id);
    if (!campagne) {
      return res.status(404).json({ message: 'Campagne non trouvée' });
    }
    res.status(200).json(campagne);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Mettre à jour une campagne
exports.mettreAJour = async (req, res) => {
  try {
    const campagne = await campagneService.mettreAJour(req.params.id, req.body);
    if (!campagne) {
      return res.status(404).json({ message: 'Campagne non trouvée' });
    }
    res.status(200).json({
      message: 'Campagne mise à jour avec succès',
      data: campagne
    });
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};

// ✅ Supprimer une campagne
exports.supprimer = async (req, res) => {
  try {
    const campagne = await campagneService.supprimer(req.params.id);
    if (!campagne) {
      return res.status(404).json({ message: 'Campagne non trouvée' });
    }
    res.status(200).json({ message: 'Campagne supprimée avec succès' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Obtenir les campagnes actives
exports.obtenirActives = async (req, res) => {
  try {
    const campagnes = await campagneService.obtenirActives();
    res.status(200).json(campagnes);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Supprimer toutes les campagnes
exports.supprimerToutes = async (req, res) => {
  try {
    await campagneService.supprimerToutes();
    res.status(200).json({ message: 'Toutes les campagnes ont été supprimées avec succès' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};



