# FAB Re-Frame Starter Project 

[Fenton](https://github.com/ftravers) and [Alex's](https://github.com/aeberts) re-frame starter project.

This is a full-stack, single-page application starter project based on re-frame and Datomic Free. 
The goal is to have a solid base project that can be used to quickly prototype ideas. 
This is being developed on MacOS and Linux and while it may work on Windows, there are no plans to officially support it (sorry).   

- Build Tools
    - Leiningen
    - shadow-cljs

- Editors Supported (out of the box)
    - [Cursive](https://cursive-ide.com/)
    - [Emacs / Cider](https://docs.cider.mx/cider/index.html)

- Dev Tools
    - [CLJS DevTools](https://github.com/binaryage/cljs-devtools)
    - [Dirac]() (not yet implemented)

- Front End
    - ClojureScript, Re-Frame and Reagent
    - GardenCSS (not yet implemented)
    - Vanilla ReactJS components (via shadow-cljs) 
    
- Back End
    - [Datomic Free](https://my.datomic.com/downloads/free)
    - [Reitit](https://www.metosin.fi/blog/reitit/) 
    - [Integrant](https://github.com/weavejester/integrant) (not yet implemented)  

- Deployment
    - Deployment to Heroku (not yet implemented)

- Testing
    - Karma (not yet implemented)


### Getting Started
Install the dependencies in <a href="#environment-setup">environment setup</a> 

Install the javascript project dependencies:
```npm install```

Run the development environment:
```lein dev```

To get a clojure repl simply connect to the nrepl server running on port 8777

To get a clojurescript repl connect to a clojure repl and then in the repl run the following command:

```clojurescript
(shadow.cljs.devtools.api/nrepl-select :app)
```  

### Features

- Hot Code Reloading (via shadow-cljs)
- Configuration Management

### Environment Setup
<a name="environment-setup"/>

1. Install [JDK 8 or later](https://openjdk.java.net/install/) (Java Development Kit)
2. Install [Leiningen](https://leiningen.org/#install) (Clojure/ClojureScript project task &
dependency management)
3. Install [Node.js](https://nodejs.org/) (JavaScript runtime environment) which should include
   [NPM](https://docs.npmjs.com/cli/npm) or if your Node.js installation does not include NPM also install it.
4. Install [karma-cli](https://www.npmjs.com/package/karma-cli) (test runner):
    ```sh
    npm install -g karma-cli
    ```
5. Install [Chrome](https://www.google.com/chrome/)
6. Install [Datomic Free](https://my.datomic.com/downloads/free)
7. Install [clj-kondo](https://github.com/borkdude/clj-kondo/blob/master/doc/install.md) (linter)
8. Clone this repo and open a terminal in the `fab` project root directory
9. (Optional) Download project dependencies:
    ```sh
    npm install
    ```