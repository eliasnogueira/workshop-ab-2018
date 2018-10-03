/*
 * MIT License
 *
 * Copyright (c) 2018 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
const routes = require('express').Router();
var bodyParser = require('body-parser')
var Contato = require('../object/Contato.js');
var data = require('../data.json');

var jsonParser = bodyParser.json();

routes.get('/api/v1/person', (req, res) => {
  res.status(200).json(data);
});

routes.get('/api/v1/person/:id', (req, res) => {
  var id = req.params.id;

  res.status(200).json(getContatoByID(id));
});

routes.post('/api/v1/person/', jsonParser, (req, res) => {
  var contato = new Contato(
    nextIDFromContactData(data),
    req.body.nome,
    req.body.endereco,
    req.body.hobbies
  );
  data.push(contato);

  res.status(200).json(contato);
});

routes.put('/api/v1/person/:id', jsonParser, (req, res) => {
  var id = req.params.id;
  for (i in data) {
    var contato;
    if (data[i].id == id) {
      console.log(data[i]);
      data[i].nome = req.body.nome;
      data[i].endereco = req.body.endereco;
      data[i].hobbies = req.body.hobbies;

      contato = data[i];
      break;
    }
  }
  res.status(200).json(contato);
});

routes.delete('/api/v1/person/:id', (req, res) => {
  var id = req.params.id;

  res.status(200).json(deleteContatoByID(id));
});

getContatoByID = function (id) {
  var contato;
  for (i in data) {
    if (data[i].id == id) {
      contato = returnContact(data, i);
      break;
    }
  }
  return contato;
};

deleteContatoByID = function (id) {
  var contato;

  for (i in data) {
    if (data[i].id == id) {
      contato = returnContact(data, i);
      data.splice(i, 1);
    }
  }

  return contato;
}

returnContact = function (data, positionOnArray) {
  return contato = new Contato(
    data[positionOnArray].id,
    data[positionOnArray].nome,
    data[positionOnArray].endereco,
    data[positionOnArray].hobbies
  );
}

nextIDFromContactData = function (data) {
  var tamanho = Object.keys(data).length - 1;
  return data[tamanho].id + 1;
}

module.exports = routes;