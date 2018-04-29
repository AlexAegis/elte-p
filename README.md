#

# Developement Environment

- IntelliJ IDEA
- Android SDK

# Tervek

Nem tudom ezekből mire lesz szükség, miket biztosít a keretrendszer de ezeket fogjuk felhasználni a játékmechanikához:

class Position
- x : double/int - x koordináta
- y : double/int - y koordináta

class Bike
- position : Position - A karakter pozíciója
- direction : 
- lastSegment : Wall - Utolsó falpozíció amivel húzza az aktív falat
- color - a színe a motornak
- function crossingWall : Boolean - Függvény ami igaz ha keresztez egy másik falat,a zaz ha ütközik.
- function spawnWall : Position - kanyarodásnál fog meghívódni, elkészít egy falszegmenst a Bike lastSegment mezőjébe, ami ottvolt meg bekerül az új szegmens lastSegment mezőjébe.

class Wall
- (position, color, lastSegment) : Constructor - elkészít egy fal objektumot 
- color : Color - a fal színe
- position : Position - fal pozíciója
- width : float - fal szélessége (Nem biztos, hogy felhasználjuk)
- lastSegment : Wall - előző fal. ennek és az előző fal pozíciója alapján lesznek a falak kirajzolva.

class Player
- bike - 
- control
