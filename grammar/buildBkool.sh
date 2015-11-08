#!/bin/bash

java -jar ./antlr-4.5.1-complete.jar BKOOL.g4 -visitor -listener -o ./target/generated-sources/antlr4/bkool/parser

echo Finished Bulding!
