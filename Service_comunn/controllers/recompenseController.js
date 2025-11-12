const Recompense = require('../models/Recompense.js');

const recompenseService = require('../services/recompenseService');

// ✅ Créer une récompense
exports.creerRecompense = async (req, res) => {
  try {
    const recompense = await recompenseService.creerRecompense(req.body);
    res.status(201).json({
      message: 'Récompense créée avec succès',
      data: recompense
    });
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};

// ✅ Obtenir toutes les récompenses
exports.obtenirToutes = async (req, res) => {
  try {
    const recompenses = await recompenseService.obtenirToutes();
    res.status(200).json(recompenses);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Obtenir une récompense par ID
exports.obtenirParId = async (req, res) => {
  try {
    const recompense = await recompenseService.obtenirParId(req.params.id);
    if (!recompense) {
      return res.status(404).json({ message: 'Récompense non trouvée' });
    }
    res.status(200).json(recompense);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Mettre à jour une récompense
exports.mettreAJour = async (req, res) => {
  try {
    const recompense = await recompenseService.mettreAJour(req.params.id, req.body);
    if (!recompense) {
      return res.status(404).json({ message: 'Récompense non trouvée' });
    }
    res.status(200).json({
      message: 'Récompense mise à jour avec succès',
      data: recompense
    });
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};

// ✅ Supprimer une récompense
exports.supprimer = async (req, res) => {
  try {
    const recompense = await recompenseService.supprimer(req.params.id);
    if (!recompense) {
      return res.status(404).json({ message: 'Récompense non trouvée' });
    }
    res.status(200).json({ message: 'Récompense supprimée avec succès' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Obtenir les récompenses d’un utilisateur
exports.obtenirParUserId = async (req, res) => {
  try {
    const recompenses = await recompenseService.obtenirParUserId(req.params.userId);
    if (!recompenses || recompenses.length === 0) {
      return res.status(404).json({ message: 'Aucune récompense trouvée pour cet utilisateur' });
    }
    res.status(200).json(recompenses);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Supprimer toutes les récompenses
exports.supprimerTous = async (req, res) => {
  try {
    await recompenseService.supprimerTous();
    res.status(200).json({ message: 'Toutes les récompenses ont été supprimées avec succès' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

