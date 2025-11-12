const mongoose = require('mongoose');
const dotenv = require('dotenv');
const Campagne = require('../models/Campagne');

dotenv.config();

mongoose.connect(process.env.MONGO_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

async function seedCampagnes() {
  try {
    await Campagne.deleteMany();

    const campagnes = [
      {
        nom: 'Campagne Fidélité Restauration',
        description: 'Programme de fidélité pour les clients réguliers.',
        type: 'Fidélité',
        dateDebut: new Date('2025-01-01'),
        dateFin: new Date('2025-12-31'),
        remise: 10,
        utilisateursCibles: ['user001', 'user002'],
        actif: true,
      },
      {
        nom: 'Promo Été 2025',
        description: 'Réduction spéciale sur les menus estivaux.',
        type: 'Promotion',
        dateDebut: new Date('2025-06-01'),
        dateFin: new Date('2025-08-31'),
        remise: 15,
        utilisateursCibles: ['user003', 'user004'],
        actif: true,
      },
    ];

    await Campagne.insertMany(campagnes);
    console.log('✅ Campagnes insérées avec succès !');
    mongoose.connection.close();
  } catch (error) {
    console.error('❌ Erreur lors du seed des campagnes :', error);
  }
}

seedCampagnes();
