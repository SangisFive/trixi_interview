# trixi_interview
Job interview<br/>
Java Console application for the interview:
https://github.com/SangisFive/trixi_interview/tree/main/trixi_software_interview


Backend with a single endpoint returning a hardcoded xml file for download testing:
https://github.com/SangisFive/trixi_interview/tree/main/backend_with_file_for_testing 

Assignment: 
Zadání:
Vytvořte javovskou aplikaci, která stáhne data v xml z internetu, zpracuje je a uloží do sql databáze.
Zazipované XML na URL https:[THIS PART IS HIDDEN DUE TO BOTHERING OF THE ORIGINAL SERVER]/vymenny_format/soucasna/20200930_OB_573060_UZSZ.xml.zip obsahuje všechny adresy v obci Kopidlno. Cílem je napsat program který stáhne soubor z daného URL, zparsuje data z XML a (některá) uloží do DB.
Měli by jste vytvořit jednoduchou databázi, ve které budou dvě tabulky - pro obec a pro část obce. U obce stačí do DB vložit kód a název, u části obce kód, název a kód obce, ke které část obce patří. Program by měl tyto dvě tabulky naplnit (V tom XML by měla být jedna obec - element vf:Obec - a několik málo částí obce - vf:CastObce). Program nemusí databázové schéma vytvářet, to stačí udělat ručně.
Pro parsování použijte nějaký standardní nástroj (který bude používat DOM, SAX nebo StAX). Není nutné při parsování získat všechna data, co jsou v XML, stačí z něj získat jen ta, která budete dávat do DB.
Pro vývoj doporučujeme si XML ručně stáhnout, na něm vyvíjet (aby se při vývoji nezatěžoval server cuzk.cz) a poté celý program spojit tak, že bude umět soubor stáhnout i zpracovat.
Jako databázi lze použít libovolnou SQL databázi. Program by měl být napsán v Javě.
