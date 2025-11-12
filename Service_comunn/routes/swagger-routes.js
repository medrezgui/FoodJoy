const express = require('express');
const router = express.Router();

const campagneController = require('./campagneController');
const recompenseController = require('./recompenseController');
const historiqueController = require('./historiqueController');

/**
 * ================================
 *        CAMPAGNES
 * ================================
 */

/**
 * @swagger
 * /campagnes:
 *   get:
 *     summary: Lister toutes les campagnes
 *     responses:
 *       200:
 *         description: Liste des campagnes
 */
router.get('/campagnes', campagneController.getCampagnes);

/**
 * @swagger
 * /campagnes:
 *   post:
 *     summary: Créer une nouvelle campagne
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               nom:
 *                 type: string
 *               description:
 *                 type: string
 *               type:
 *                 type: string
 *                 enum: [Fidélité, Promotion, Evenement]
 *               dateDebut:
 *                 type: string
 *                 format: date
 *               dateFin:
 *                 type: string
 *                 format: date
 *               remise:
 *                 type: number
 *               utilisateursCibles:
 *                 type: array
 *                 items:
 *                   type: string
 *     responses:
 *       201:
 *         description: Campagne créée
 */
router.post('/campagnes', campagneController.createCampagne);

/**
 * @swagger
 * /campagnes/{id}:
 *   put:
 *     summary: Mettre à jour une campagne
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *     responses:
 *       200:
 *         description: Campagne mise à jour
 */
router.put('/campagnes/:id', campagneController.updateCampagne);

/**
 * @swagger
 * /campagnes/{id}:
 *   delete:
 *     summary: Supprimer une campagne
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Campagne supprimée
 */
router.delete('/campagnes/:id', campagneController.deleteCampagne);

/**
 * ================================
 *        HISTORIQUES
 * ================================
 */

/**
 * @swagger
 * /historiques:
 *   get:
 *     summary: Lister tous les historiques
 *     responses:
 *       200:
 *         description: Liste des historiques
 */
router.get('/historiques', historiqueController.getHistoriques);

/**
 * @swagger
 * /historiques:
 *   post:
 *     summary: Ajouter un historique
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               userId:
 *                 type: string
 *               campagneId:
 *                 type: string
 *               remiseAppliquee:
 *                 type: number
 *               commandeId:
 *                 type: string
 *     responses:
 *       201:
 *         description: Historique créé
 */
router.post('/historiques', historiqueController.createHistorique);

/**
 * @swagger
 * /historiques/user/{userId}:
 *   get:
 *     summary: Lister les historiques d’un utilisateur
 *     parameters:
 *       - in: path
 *         name: userId
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Historique de l’utilisateur
 */
router.get('/historiques/user/:userId', historiqueController.getHistoriquesByUser);

/**
 * @swagger
 * /historiques/campagne/{campagneId}:
 *   get:
 *     summary: Lister les historiques d’une campagne
 *     parameters:
 *       - in: path
 *         name: campagneId
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Historique de la campagne
 */
router.get('/historiques/campagne/:campagneId', historiqueController.getHistoriquesByCampagne);

/**
 * ================================
 *        RECOMPENSES
 * ================================
 */

/**
 * @swagger
 * /recompenses:
 *   get:
 *     summary: Lister toutes les récompenses
 *     responses:
 *       200:
 *         description: Liste des récompenses
 */
router.get('/recompenses', recompenseController.getRecompenses);

/**
 * @swagger
 * /recompenses:
 *   post:
 *     summary: Créer une récompense
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               userId:
 *                 type: string
 *               description:
 *                 type: string
 *               valeur:
 *                 type: number
 *     responses:
 *       201:
 *         description: Récompense créée
 */
router.post('/recompenses', recompenseController.createRecompense);

/**
 * @swagger
 * /recompenses/user/{userId}:
 *   get:
 *     summary: Lister les récompenses d’un utilisateur
 *     parameters:
 *       - in: path
 *         name: userId
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Récompenses de l’utilisateur
 */
router.get('/recompenses/user/:userId', recompenseController.getRecompensesByUser);

/**
 * @swagger
 * /recompenses/{id}/utiliser:
 *   put:
 *     summary: Marquer une récompense comme utilisée
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Récompense mise à jour
 */
router.put('/recompenses/:id/utiliser', recompenseController.utiliserRecompense);

module.exports = router;
