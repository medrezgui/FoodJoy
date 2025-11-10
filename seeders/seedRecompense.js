const mongoose = require('mongoose');
const dotenv = require('dotenv');
const Recompense = require('../models/Recompense');

dotenv.config();

mongoose.connect(process.env.MONGO_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

async function seedRecompenses() {
  try {
    await Recompense.deleteMany();

    const recompenses = [
      {
        userId: 'user001',
        type: 'Bon',
        valeur: 20,
        utilise: false,
        historiqueId: 'hist001',
      },
      {
        userId: 'user002',
        type: 'Cadeau',
        valeur: 1,
        utilise: true,
        historiqueId: 'hist002',
      },
    ];

    await Recompense.insertMany(recompenses);
    console.log('✅ Récompenses insérées avec succès !');
    mongoose.connection.close();
  } catch (error) {
    console.error('❌ Erreur lors du seed des récompenses :', error);
  }
}

seedRecompenses();
