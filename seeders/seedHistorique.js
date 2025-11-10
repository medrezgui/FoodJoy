const mongoose = require('mongoose');
const dotenv = require('dotenv');
const Historique = require('../models/Historique');

dotenv.config();

mongoose.connect(process.env.MONGO_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

async function seedHistoriques() {
  try {
    await Historique.deleteMany();

    const historiques = [
      {
        userId: 'user001',
        campagneId: 'campagne001',
        dateApplication: new Date('2025-03-12'),
        remiseAppliquee: 10,
        commandeId: 'cmd001',
      },
      {
        userId: 'user002',
        campagneId: 'campagne002',
        dateApplication: new Date('2025-04-10'),
        remiseAppliquee: 15,
        commandeId: 'cmd002',
      },
    ];

    await Historique.insertMany(historiques);
    console.log('✅ Historiques insérés avec succès !');
    mongoose.connection.close();
  } catch (error) {
    console.error('❌ Erreur lors du seed des historiques :', error);
  }
}

seedHistoriques();
