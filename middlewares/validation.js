const Joi = require('joi');

// --------------------
// Schemas
// --------------------

// Campagne
const campagneSchema = Joi.object({
  nom: Joi.string().min(3).max(100).required(),
  description: Joi.string().max(500).allow(''),
  type: Joi.string().valid('Fidélité', 'Promotion', 'Evenement').required(),
  dateDebut: Joi.date().required(),
  dateFin: Joi.date().required(),
  remise: Joi.number().min(0).required(),
  utilisateursCibles: Joi.array().items(Joi.string()),
  actif: Joi.boolean()
});

// Historique
const historiqueSchema = Joi.object({
  userId: Joi.string().required(),
  campagneId: Joi.string().required(),
  dateApplication: Joi.date(),
  remiseAppliquee: Joi.number().min(0),
  commandeId: Joi.string().allow('')
});

// Récompense
const recompenseSchema = Joi.object({
  userId: Joi.string().required(),
  type: Joi.string().valid('Bon', 'Remise', 'Cadeau').required(),
  valeur: Joi.number().min(0).required(),
  dateObtention: Joi.date(),
  utilise: Joi.boolean(),
  historiqueId: Joi.string().allow('')
});

// --------------------
// Middleware de validation
// --------------------
const validationMiddleware = (schema) => {
  return (req, res, next) => {
    const { error } = schema.validate(req.body);
    if (error) {
      return res.status(400).json({ message: error.details[0].message });
    }
    next();
  };
};

// --------------------
// Exporter tout
// --------------------
module.exports = {
  campagneSchema,
  historiqueSchema,
  recompenseSchema,
  validationMiddleware
};
