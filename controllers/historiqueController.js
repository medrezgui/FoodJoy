const Historique = require('../models/Historique');


const historiqueService = require('../services/historiqueService');

// ✅ Créer un historique
exports.creerHistorique = async (req, res) => {
  try {
    const historique = await historiqueService.creerHistorique(req.body);
    res.status(201).json({
      message: 'Historique créé avec succès',
      data: historique
    });
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};

// ✅ Obtenir tous les historiques
exports.obtenirTous = async (req, res) => {
  try {
    const historiques = await historiqueService.obtenirTous();
    res.status(200).json(historiques);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Obtenir un historique par ID
exports.obtenirParId = async (req, res) => {
  try {
    const historique = await historiqueService.obtenirParId(req.params.id);
    if (!historique) {
      return res.status(404).json({ message: 'Historique non trouvé' });
    }
    res.status(200).json(historique);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Obtenir les historiques d’un utilisateur
exports.obtenirParUserId = async (req, res) => {
  try {
    const historiques = await historiqueService.obtenirParUserId(req.params.userId);
    if (!historiques || historiques.length === 0) {
      return res.status(404).json({ message: 'Aucun historique trouvé pour cet utilisateur' });
    }
    res.status(200).json(historiques);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Supprimer un historique par ID
exports.supprimer = async (req, res) => {
  try {
    const historique = await historiqueService.supprimer(req.params.id);
    if (!historique) {
      return res.status(404).json({ message: 'Historique non trouvé' });
    }
    res.status(200).json({ message: 'Historique supprimé avec succès' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// ✅ Supprimer tous les historiques
exports.supprimerTous = async (req, res) => {
  try {
    await historiqueService.supprimerTous();
    res.status(200).json({ message: 'Tous les historiques ont été supprimés avec succès' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

