JC = javac -g -d out
DIR = ./src/Com/advancedOS/RestaurantManger

all: classes
	
classes:
	mkdir out; $(JC) $(DIR)/*.java $(DIR)/Resources/*.java

clean:
	rm -rf out  

