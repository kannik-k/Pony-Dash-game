# Pony Dash For Spikes Salvation

Pony Dash For Spikes Salvation on võistlusmäng, kus ponid läbivad takistustega ja lobisevate külaelanikega kaardi, et päästa oma sõber Spike. Mängida saab üksinda või kaasmängijatega: võidab see, kes esimesena Spike'i päästab.

Mängu sisenedes on võimalik valida, kas mängida üksi või mitmekesi. Edasi saab valida nime ja tegelase. Mängu reegleid saab lugeda Tutorial ekraanilt ning Settings ekraanilt saab muuta muusika ning heliefektide tugevust. Mängijad hakkavad läbima kaarti, ületades takistusi. Kaart on jagatud erinevateks osadeks ning iga osa takistused lähevad aina raskemaks. Kaardil liiguvad ringi ka külaelanikud, kes mängija läheduses hakkavad külajutte rääkima ja kulutavad mängija aega. Kaardi peal on ka mündid. Kaardil olevatest vahepunktidest edasi liikumiseks peab olema kogutud mingi kindel arv münte. Lisaks on kahte tüüpi võimeid: õunad annavad lisakiirust ning kirsid hüppeõrgust. Neid saab teiste mängijate eest ära varastada. Takistuste vahel on augud ja okkad, mille sisse kukkudes või pihta minnes viiakse mängija kaardiosa algusesse tagasi. Võidab mängija, kes esimesena lõppu jõuab ja Spike'i päästab. Spike'i päästmiseks on kolmandast kaardisosast vaja üles leida ka võti.

# Mängu jooksutamine

Mängimiseks on vaja IntelliJ-sse alla kloonida serveri (iti0301-2024-server) ja kliendi (iti0301-2024-game) repositooriumite HTTP lingid. Projekt tuleb ühendada Gradle'ga. Järgmiseks tuleb liikuda serveri repositooriumis GameServer klassi ja seda jooksutada. Edasi tuleb liikuda kliendi repositooriumis DesktopLauncher klassi ja seda jooksutada. Multiplayer'i saab tööle, kui DesktopLauncher'it mitu korda jooksutada. Kui tuleb ette error, siis tuleb avada üleval ribal "Run" nupust vasakul valge kolmnurk ning "Edit configurations...". Sealt edasi "Modify options" ja "Allow multiple instances". Kooli serveriga ühendamiseks tuleb liikuda Main klassi ning vaadata, et rida "client.connect(5000, "193.40.255.33", 8080, 8081);" ei oleks välja kommenteeritud. Samal ajal peab rida "client.connect(5000, "localhost", 8080, 8081);" olema välja kommenteeritud.

# Autorid
Karoliina Kannik, Aet-Kadi Kald, Faina Dõmša
