'use strict';
var util = require('util'),
    path = require('path'),
    yeoman = require('yeoman-generator'),
    _ = require('lodash'),
    _s = require('underscore.string'),
    pluralize = require('pluralize'),
    asciify = require('asciify');

var AngularLuminusGenerator = module.exports = function AngularLuminusGenerator(args, options, config) {
  yeoman.generators.Base.apply(this, arguments);

  this.on('end', function () {
    this.installDependencies({ skipInstall: options['skip-install'] });
  });

  this.pkg = JSON.parse(this.readFileAsString(path.join(__dirname, '../package.json')));
};

util.inherits(AngularLuminusGenerator, yeoman.generators.Base);

AngularLuminusGenerator.prototype.askFor = function askFor() {

  var cb = this.async();

  console.log('\n' +
    '+-+-+-+-+-+-+-+ +-+-+-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+\n' +
    '|a|n|g|u|l|a|r| |l|u|m|i|n|u|s| |g|e|n|e|r|a|t|o|r|\n' +
    '+-+-+-+-+-+-+-+ +-+-+-+-+-+-+-+ +-+-+-+-+-+-+-+-+-+\n' +
    '\n');

  var prompts = [{
    type: 'input',
    name: 'baseName',
    message: 'What is the name of your application?',
    default: 'myapp'
  }];

  this.prompt(prompts, function (props) {
    this.baseName = props.baseName;

    cb();
  }.bind(this));
};

AngularLuminusGenerator.prototype.app = function app() {

  this.entities = [];
  this.resources = [];
  this.generatorConfig = {
    "baseName": this.baseName,
    "entities": this.entities,
    "resources": this.resources
  };
  this.generatorConfigStr = JSON.stringify(this.generatorConfig, null, '\t');

  this.template('_generator.json', 'generator.json');
  this.template('_package.json', 'package.json');
  this.template('_bower.json', 'bower.json');
  this.template('bowerrc', '.bowerrc');
  this.template('Gruntfile.js', 'Gruntfile.js');
  this.copy('gitignore', '.gitignore');

  var publicDir = 'resources/public/'
  var migrationsDir = 'migrations/'
  var srcDir = 'src/'
  var appDir = srcDir + this.baseName + '/';
  var modelsDir = appDir + 'models/'
  var routesDir = appDir + 'routes/'
  var viewsDir = appDir + 'views/'
  var testDir = 'test/' + this.baseName + '/test/';
  this.mkdir(publicDir);
  this.mkdir(migrationsDir);
  this.mkdir(srcDir);
  this.mkdir(modelsDir);
  this.mkdir(routesDir);
  this.mkdir(viewsDir);
  this.mkdir(testDir);

  this.copy('Procfile', 'Procfile');
  this.template('_project.clj', 'project.clj');
  this.template('src/log4j.xml', srcDir + 'log4j.xml');
  this.template('src/app/_handler.clj', appDir + 'handler.clj');
  this.template('src/app/_repl.clj', appDir + 'repl.clj');
  this.template('src/app/models/_db.clj', modelsDir + 'db.clj');
  this.template('src/app/models/_schema.clj', modelsDir + 'schema.clj');
  this.template('src/app/routes/_home.clj', routesDir + 'home.clj');
  this.template('test/app/test/_handler.clj', testDir + 'handler.clj');

  var publicCssDir = publicDir + 'css/';
  var publicJsDir = publicDir + 'js/';
  var publicViewDir = publicDir + 'views/';
  this.mkdir(publicCssDir);
  this.mkdir(publicJsDir);
  this.mkdir(publicViewDir);
  this.template('public/_index.html', publicDir + 'index.html');
  this.copy('public/css/app.css', publicCssDir + 'app.css');
  this.template('public/js/_app.js', publicJsDir + 'app.js');
  this.template('public/js/home/_home-controller.js', publicJsDir + 'home/home-controller.js');
  this.template('public/views/home/_home.html', publicViewDir + 'home/home.html');
};

AngularLuminusGenerator.prototype.projectfiles = function projectfiles() {
  this.copy('editorconfig', '.editorconfig');
  this.copy('jshintrc', '.jshintrc');
};
