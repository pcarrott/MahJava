# Compiling Latex

## Using tectonic
Simply install the [tectonic package](https://tectonic-typesetting.github.io/en-US/install.html#pre-built-binary-packages) and run:
```
tectonic <file>.tex
```
It should create a <file>.pdf. 

## Using texlive
Install the texlive-full package
```
sudo apt install texlive-full
```

Then run `pdflatex`, `bibtex` and `pdflatex` again. If you run without the `-interaction nonstopmode` flag, should press Enter for every prompt.
```
pdflatex -interaction nonstopmode proposal.tex
bibtex proposal.aux
pdflatex -interaction nonstopmode proposal.tex
```
After all the garbage, there should be a well-typed `proposal.pdf` file in the directory.

## Using lualatex
Fuck this shit with the force of a thousand suns
