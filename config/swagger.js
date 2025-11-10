const swaggerJsdoc = require('swagger-jsdoc');
const swaggerUi = require('swagger-ui-express');

const options = {
  definition: {
    openapi: "3.0.0",
    info: {
      title: "API Marketing Restauration",
      version: "1.0.0",
      description: "Documentation des APIs pour les campagnes, historiques et récompenses",
    },
    servers: [
      { url: "http://localhost:5000" }
    ],
  },
  apis: ["./routes/swagger-routes.js"], // fichiers où on documente les routes
};

const specs = swaggerJsdoc(options);

module.exports = (app) => {
  app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(specs));
};
