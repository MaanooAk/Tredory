MVN = mvn -q

.phony: default
default: build run

# TODO put mvn in a variable

.phony: build
build:
	$(MVN) package

.phony: clean
clean:
	$(MVN) clean

# TODO should probably change version detection technique
# TODO detect if run and debug need build

.phony: run
run:
	./target/Tredory-*-release/Tredory-*/game.sh

.phony: debug
debug:
	./target/Tredory-*-release/Tredory-*/game.sh --debug

# TODO add help
