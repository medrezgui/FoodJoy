const express = require('express');
const router = express.Router();

const campagneController = require('../controllers/campagneController');
const historiqueController = require('../controllers/historiqueController');
const recompenseController = require('../controllers/recompenseController');

const { validationMiddleware, campagneSchema, historiqueSchema, recompenseSchema } = require('../middlewares/validation');

// --------------------
// Routes Campagnes
// --------------------
router.post('/campagnes', validationMiddleware(campagneSchema), campagneController.creerCampagne);
router.get('/campagnes', campagneController.obtenirToutes);
router.get('/campagnes/actives', campagneController.obtenirActives);
router.get('/campagnes/:id', campagneController.obtenirParId);
router.put('/campagnes/:id', validationMiddleware(campagneSchema), campagneController.mettreAJour);
router.delete('/campagnes/:id', campagneController.supprimer);
router.delete('/campagnes', campagneController.supprimerToutes);

// --------------------
// Routes Historiques
// --------------------
router.post('/historiques', validationMiddleware(historiqueSchema), historiqueController.creerHistorique);
router.get('/historiques', historiqueController.obtenirTous);
router.get('/historiques/:id', historiqueController.obtenirParId);
router.get('/historiques/user/:userId', historiqueController.obtenirParUserId);
router.delete('/historiques/:id', historiqueController.supprimer);
router.delete('/historiques', historiqueController.supprimerTous);

// --------------------
// Routes Récompenses
// --------------------
router.post('/recompenses', validationMiddleware(recompenseSchema), recompenseController.creerRecompense);
router.get('/recompenses', recompenseController.obtenirToutes);
router.get('/recompenses/:id', recompenseController.obtenirParId);
router.put('/recompenses/:id', validationMiddleware(recompenseSchema), recompenseController.mettreAJour);
router.get('/recompenses/user/:userId', recompenseController.obtenirParUserId);
router.delete('/recompenses/:id', recompenseController.supprimer);
router.delete('/recompenses', recompenseController.supprimerTous);

module.exports = router;
