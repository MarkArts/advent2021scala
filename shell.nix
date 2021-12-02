let
  pkgs = import <nixpkgs> {};
in 
  pkgs.mkShell {
    buildInputs = [
      pkgs.coursier
      pkgs.jdk11
      pkgs.sbt
    ];

    shellHook = ''
      export PATH="$PATH:/home/mark/.local/share/coursier/bin"
    '';
  }