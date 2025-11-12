// middleware/errorHandler.js

function errorHandler(err, req, res, next) {
  console.error('🚨 Erreur détectée :', err.stack || err);

  // Si le message d'erreur contient déjà un code, on le garde
  const statusCode = err.status || 500;
  const message = err.message || 'Erreur interne du serveur';

  res.status(statusCode).json({
    success: false,
    message,
  });
}

module.exports = errorHandler;
