const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');
const bodyParser = require('body-parser');

const routes = require('./routes/routes'); // <-- ton routes.js
const swaggerSetup = require('./config/swagger'); // <-- pas './swagger' mais './config/swagger'



dotenv.config();
const app = express();
app.use(bodyParser.json());

// Middleware global
app.use(bodyParser.json());

// Connexion MongoDB
mongoose.connect(process.env.MONGO_URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true
})
.then(() => console.log('MongoDB connecté'))
.catch(err => console.log('Erreur MongoDB :', err));

// Routes
//app.use('/api/campagnes', campagneRoutes);

// Utiliser les routes
//app.use('/', routes);
// Routes principales

app.use('/api', routes);

swaggerSetup(app);
require('./cronJobs');

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));

