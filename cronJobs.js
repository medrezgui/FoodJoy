const cron = require('node-cron');
const campagneService = require('./services/campagneService');
// const recompenseService = require('./services/recompenseService'); // si tu veux faire la même chose pour récompenses

// Exécuter toutes les heures par exemple
cron.schedule('0 * * * *', async () => {
  console.log('🔔 Vérification des campagnes expirées...');
  try {
    const result = await campagneService.desactiverCampagnesExpirees();
    console.log(`✅ ${result.nModified} campagnes désactivées.`);
  } catch (err) {
    console.error('Erreur GrandJobs campagne:', err);
  }

  // Idem si tu veux gérer récompenses automatiquement
  // await recompenseService.traiterRecompensesHistoriquesExpirees();
});

