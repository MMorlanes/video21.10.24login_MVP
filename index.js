const express = require("express");
const cors = require("cors");
const mysql = require("mysql2/promise");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
require("dotenv").config();

const app = express();
const PORT = 3000;

const myPool = mysql.createPool({
  host: "localhost",
  user: "root",
  password: "",
  database: "video21.10.24",
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0,
});

app.use(cors());
app.use(express.json());

// Iniciar el servidor
app.listen(PORT, () => {
  console.log("Servidor funcionando!!!");
  console.log(`El servidor está escuchando en http://localhost:${PORT}`);
});

// Registro de usuarios
app.post("/register", async (req, res) => {
  const { username, password, email } = req.body;

  try {
    const hashedPassword = await bcrypt.hash(password, 10);
    const query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
    const [result] = await myPool.query(query, [username, hashedPassword, email]);
    res.status(201).json({ message: "Usuario registrado exitosamente", userId: result.insertId });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: "Error al registrar el usuario" });
  }
});

//Login de usuarios
app.get('/login', async (req, res) => {
    const { username, password } = req.query; 
    console.log(req.query);

    try {
        const [user] = await myPool.query('SELECT id, username FROM users WHERE username = ? AND password = ?', [username, password]);

        if (user.length > 0) {
            console.log("Sesión iniciada");
            res.status(200).json({ message: 'Inicio de sesión exitoso', user: { id: user[0].id, username: user[0].username } });
        } else {
            res.status(401).json({ error: 'Credenciales incorrectas' });
        }

    } catch (error) {
        console.error('Error al iniciar sesión:', error);
        res.status(500).json({ error: 'Error interno del servidor' });
}
});


