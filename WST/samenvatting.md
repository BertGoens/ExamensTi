###4 edities van Windows Server 2012 R2:
- Foundation: enkel als OEM-versie verkrijgbaar
- Essentials: voor kleine bedrijven met tot 25 gebruikers en 50 toestellen
- Standard: voor bedrijven waar weinig of geen virtualisatie toegepast wordt
- Datacenter: voor datacenters waar veel virtualisatie toegepast wordt en cloud omgevingen

---

###Verschil tussen core & GUI
- Server Core biedt geen GUI
- Server Core is kleiner in omvang (4 GB kleiner)
- Server Core is beter bestand tegen aanvallen
- Server Core ondersteunt de meeste, maar zeker niet alle roles en features (bv geen WDS)
- Server Core is lastiger om op de server zelf te beheren/configureren

---

###De Minimal Server Interface bevat:
- Microsoft Management Consoles (MSC-files)
- Server Manager
- Een beperkte subset van Control Panel

---

###Hoe aan serverbeheer op een core installation doen?
- mbv Windows-commando’s
- mbv Windows PowerShell cmdlets
- mbv Afstandsbeheer (zie later)

---

###Hoe aan serverbeheer op een full installation doen?
- mbv Server Manager (meest gebruikt)
- mbv Windows-commando’s
- mbv Windows PowerShell (ISE)
- mbv MMC’s (Microsoft Management Consoles)

---

###Verschil tussen een role & feature?
- Een “role” is iets dat de server aan clients geeft.
- De meeste rollen bestaan uit een aantal nauw met elkaar verwante “role services”.
- Een “feature” is iets dat een server zelf gebruikt.

---

###Switchen tussen installatievormen
- Van Full installation naar Core installation, of omgekeerd: verwijderen of toevoegen van de features “Server-GUI-Mgmt-Infra” en “Server-GUI-Shell”.
- Van Full Installation naar Minimal Interface, of omgekeerd: verwijderen of toevoegen van de feature “Server-GUI-Shell”.
- Van Minimal Interface naar Core installation, of omgekeerd: verwijderen of toevoegen van de feature “Server-GUI-Mgmt-Infra”.

---

###Beheer van op afstand met?
- Vanaf een Windows-computer via een Remote Desktop connectie; dit moet dan wel expliciet toegestaan worden op de remote server (zie later)
- Vanaf een Windows-computer waarop de Remote Server Administration Tools (RSAT) geïnstalleerd zijn;
- Vanaf een andere Windows-computer mbv Windows Remote Management (WinRM); dit is vooral handig om een core remote te beheren

---

###Powershell remoting
- Starten van een interactieve PS-sessie op een andere server: mbv het PS-commando: enter-pssession
- Uitvoeren van een remote commando: mbv het PS-commando: invoke-command
- Web based PowerShell: hiervoor moet je de corresponderende feature installeren

---

###Windows-netwerkmodellen
- Werkgroepmodel
    - Gedecentraliseerd beheer: lokale user database (SAM) op elke server; lokale policies (dus enkel geldig voor die ene server)
    - 1 soort server = stand-alone server
    - Enkel interessant voor heel kleine netwerken
- Domeinmodel
    - Gecentraliseerd beheer: domain user database in Active Directory op een domeincontroller; domain based policies
    - 2 soorten servers: domeincontrollers en member servers
    - Meest gebruikt

---
	
###Soorten domeinmodellen
- Single domain model: bestaat uit één enkel domein (eenvoudigst)
- Domain tree: bestaat uit verschillende domeinen in een boomstructuur (hiërarchisch model)
- Forest: 1 of meer domain trees die met elkaar verbonden zijn

---

###2 soorten DC’s
- Writable DC’s: bevatten een AD waarin rechtstreeks wijzigingen kunnen aangebracht worden
- Read Only DC’s (RODC’s): bevatten een AD waarin geen rechtstreekse wijzigingen kunnen aangebracht worden; ze ondersteunen enkel inbound replication

---

###In een domain tree model is de vertrouwensrelatie steeds
- Bidirectioneel: als A~B dan ook B~A
- Transitief: als A~B en B~C dan ook A~C

---

###Computers toevoegen aan het domein (3 opties):
- mbv Server Manager
- mbv PS-cmdlet Add-Computer
- mbv de sconfig tool

---

###De Active Directory is standaard opgebouwd uit 3 partities:
- De domein partitie
    - bevat objecten van een domein
    - wordt naar alle DC’s in een domein gerepliceerd
- De configuratie partitie
    - bevat configuratiegegevens over trees en forests (de topologie)
    - wordt naar alle DC’s in een forest gerepliceerd
- De schema partitie
    - bevat het schema = de formele definitie van de objecten en hun attributen die in de AD bijgehouden worden
    - wordt naar alle DC’s in een forest gerepliceerd
	
---

###Operations Masters
- “Forest wide” operations masters (exact 1 per forest)
    - Schema master: voor het bijhouden en bewerken van het schema
    - Domain Naming Master: voor het toevoegen of verwijderen van domeinen in de forest
- “Domain wide” operations masters (exact 1 per domein)
    - RID Master (relatieve id): wijst aan elke user, group of computer een SID (Security ID) toe
    - PDC Emulator (PDC = Primary Domain Controller): is o.m. verantwoordelijk voor tijdsynchronisatie
    - Infrastructure Master: is verantwoordelijk voor het bijwerken van verwijzingen van objecten in het eigen domein naar objecten in andere domeinen

---

###2 soorten intersite replication
- Intrasite replication = replicatie binnen 1 site
   - Elke DC stuurt 15 seconden na een wijziging een “change notification” naar zijn replicatiepartner, die dan de gewijzigde info ophaalt (pull replication)
   - Uitzonderingen: volgende replications gebeuren onmiddellijk
       - urgent replication: bv bij een account lockout
       - password reset: wordt meteen doorgestuurd naar de PDC
   - Maakt gebruik van RPC (Remote Procedure Call) over IP
- Intersite replication = replicatie tussen sites
   - gebeurt dmv zgn. bridgehead servers
   - is standaard gepland: standaard om de 3 uur
   - doet standaard aan compressie
   - werkt standaard niet met change notifications
   - Maakt gebruik van RPC over IP of SMTP

---

###SID
- 500: Administrator 
- 501: Guest

---

###Instellen local password policy:
- Console = Local Security Policy
    - Via Server Manager - Tools - Local Security
    - Via command prompt: SECPOL.MSC
- Windows-commando: NET ACCOUNTS

###Instellen password policy op domeinniveau:
- Console = Group Policy Management
    - Via Server Manager - Tools - Group Policy Management
    - Via command prompt: GPMC.MSC
- Windows-commando: NET ACCOUNTS

###Instellen fine-grained password settings:
- Server Manager - Active Directory Administrative Center - System container - Password Settings Container
- PS-commando: New-ADFineGrainedPasswordPolicy

---

###Windows gebruikt 2 soorten hashfuncties
- LM-hash : niet zo veilig (maakt gebruik van DES); nog voorzien omwille van backward compatibility; wordt standaard niet (meer) bewaard; kan enkel gebruikt worden bij wachtwoorden van max 14 tekens
- NT(LM)-hash: veiligst (maakt gebruik van MD4); meest gebruikt

---

###Local user management
- Console = Local User Manager 
    - Via Server Manager - Tools - Computer Management
    - Via command prompt: LUSRMGR.MSC
- Windows-commando: NET USER

###Domain user management
- Console = “Active Directory Users and Computers” (ADU&C)
    - Via Server Manager - Tools
    - Via command prompt: DSA.MSC
- Windows-commando’s:
    - NET USER: beperkt aantal mogelijkheden
    - DSADD USER: uitgebreid aantal mogelijkheden
- PS-cmdlets: New-ADUser, Get-ADUser, Set-ADUser, Remove-ADUser

---

###Soorten user profiles
- Lokaal gebruikersprofiel (local user profile)
    - wordt op de lokale computer opgeslagen
    - is enkel op die computer van toepassing
    - handig voor gebruikers met vaste computer
- Zwervend gebruikersprofiel (roaming user profile) 
    - wordt op een server opgeslagen
    - is van toepassing op elke computer waarop gebruiker zich aanmeldt
    - handig voor gebruikers die niet altijd op een vaste computer werken
- Verplicht gebruikersprofiel (mandatory user profile)
    - Speciaal zwervend profiel dat niet bijgewerkt wordt bij het opslaan op de server
    - handig voor kiosken

---

###Local group management
- Console = Local User Manager 
    - Via Server Manager - Tools - Computer Management
    - Via command prompt: LUSRMGR.MSC
- Windows-commando: NET LOCALGROUP

###Domain user management
- Console = “Active Directory Users and Computers” (ADU&C)
    - Via Server Manager - Tools
    - Via command prompt: DSA.MSC
- Windows-commando’s:
    - NET GROUP: voor globale of universele groepen
    - NET LOCALGROUP … /DOMAIN : voor domain local groups
- PS-cmdlets: Add-ADGroupMember, Get-ADGroup, Get-ADGroupMember, New-ADGroup, Remove-AdGroup, Remove-ADgroupMember, Set AdGroup

---

###Soorten domeingroepen gebaseerd op het gebruik:
- Security groups
    - voor beveiliging vd toegang tot (gedeelde) mappen, printers,…
    - voor gebruik als distributielijst in mailings (door programma’s die AD-aware zijn zoals bv Exchange)
- Distribution groups
    - enkel voor gebruik als distributielijst in mailings (door programma’s die AD-aware zijn zoals bv Exchange)
	
###Soorten groepen gebaseerd op het bereik (scope):
- Domain local groups (domeingebonden groepen)
    - Bereik: beperkt tot domein waarin ze gedefinieerd zijn (enkel toegang tot resources in dat domein)
    - Leden: kunnen accounts bevatten uit alle domeinen van de forest waartoe de groep behoort
- Global groups
    - Bereik: kunnen toegang hebben tot alle resources uit de forest
    - Leden: kunnen enkel accounts bevatten uit domein waartoe de groep behoort
- Universal groups
    - Bereik: kunnen toegang hebben tot alle resources uit de forest
    - Leden: kunnen accounts bevatten uit alle domeinen van de forest waartoe de groep behoort
	
---

###Belangrijkste kenmerken van ReFS
- Data-integriteit: een data integriteitsscanner (“scrubber”) controleert en corrigeert automatisch fouten in het bestandssysteem
- Beschikbaarheid: in geval van corrupte data blijft ReFS on line
- Schaalbaarheid: geoptimaliseerd voor zeer grote datasets

---

###Opvragen/wijzigen NTFS-permissions:
- GUI: via Eigenschappenblad v/d file of folder (tab “Security”)
- Windows-Commando: ICACLS
- PS-commando’s: Get-ACL en Set-ACL

---

###2 soorten NTFS-permissions
- Standard of basic permissions: voor “gewoon” gebruik (5/6 permissions)
- Advanced of special permissions: voor “speciaal” gebruik; bereikbaar via de knop “Advanced” in de Security-tab (13 permissions)

---

###Beheer van shares: 
- Via de GUI:
    - via tab “Sharing” in het eigenschappenblad v/d map
    - via de Computermanagement-console (COMPMGMT.MSC)
    - Via Server Manager > File and Storage Services
    - Via File Sharing management console (FSMGMT.MSC)
    - Via File Server Resource Manager console (FSRM.MSC); deze moet als extra rol geïnstalleerd worden
- Mbv Windows-commando NET SHARE
- Mbv PS-commando’s: Get-SmbShare en Set-SmbShare

---

###Toegangsprotocollen voor een shared folder
- SMB (Server Message Block): is het standaard protocol dat op Windows-servers gebruikt wordt
- NFS (Network File System): is een Unix-based protocol

---

###Administrative shares
- Root-shares zoals C$, D$,… Dollarteken achter de naam v/e share duidt op een hidden share = share die niet zichtbaar is in browse lists, maar wel rechtstreeks kan benaderd worden (bv: \\192.168.17.101\C$)
- ADMIN$ = Windows system root (C:\Windows)
- NETLOGON: wordt gebruikt door de Netlogon-service; hierin worden standaard alle aanmeldingsscripts bewaard

---

###Voordelen & nadelen van offline files
- Voordelen: 
    - Offline files zijn steeds toegang toegankelijk, ook al is er geen netwerkverbinding met de server (erg handig bij laptopgebruikers)
    - Bij trage netwerkverbindingen, kan men tijdelijk met offline files werken
- Nadelen
    - Soms synchronisatieproblemen
    - Mogelijks beveiligingsprobleem
    - Nemen ook schijfruimte in op de clients
	
---

###2 soorten DFS
- Domain-based
    - Integratie met AD: DFS-topologie wordt
automatisch gepubliceerd in de AD
    - Automatische replicatie v/d DFS-topologie
    - Meer dan 1 DFS-root per server mogelijk
    - DFS-rootserver kan member server of DC zijn
- Stand-alone
    - Geen integratie met AD
    - Max 1 DFS-root per server mogelijk

---

###Beheer van DFS gebeurt mbv:
- Managementconsole: DFSMGMT.MSC
- Commando: DFSUTIL

---

###2 soorten CA’s:
- Stand-alone CA’s: maken geen gebruik van een AD en kunnen op om het even welke server geïnstalleerd worden
- Enterprise CA’s: maken gebruik van de AD en kunnen dus enkel op een server geïnstalleerd worden die lid is van een domein (hoeft zeker geen DC te zijn)

---

###3 methodes om aan een gebruikerscertificaat te geraken:
- Via de Certificaten-console (CERTMGR.MSC)
- Via “auto-enrollment” (transparant voor de gebruiker) mbv een GPO
- Via een webinterface

---

###2 soorten group policies:
- Local policies
    - kunnen enkel toegepast worden op de lokale gebruikers en/of de computer waarop ze aangemaakt worden
    - Console: GPEDIT.MSC
- Domain based policies
    - kunnen toegepast worden op domeingebruikers en/of computers die tot een domein behoren
    - Console: GPMC.MSC

---

###Indien er verschillende LGPO’s gedefinieerd werden, dan worden deze in de volgende volgorde uitgevoerd:
1. a. De LGPO Computer Configuration (ingesteld met gpedit.msc)
b. De LGPO User Configuration (ingesteld met gpedit.msc)
2. De LGPO toegepast op de groep “Administrators” of “Non-Administrators”
3. De LGPO toegepast op een specifieke gebruiker

---

###Standaard 2 built-in GPO’s:
- Default Domain Policy: policy gekoppeld aan een domein en dus standaard van toepassing op alle domeingebruikers én computers die tot dat domein behoren
- Default Domain Controllers Policy: policy gekoppeld aan de Domain Controllers OU en dus van toepassing op alle domeincontrollers

---

###Mbv een domain based GPO kan men 2 soorten policies definiëren:
- Group Policies: bevat afdwingbare instellingen die door de gebruikers niet kunnen gewijzigd worden (“greyed out” settings)
- Group Policy Preferences: bevat speciale voorkeursinstellingen die door de gebruikers wél kunnen gewijzigd worden (hoewel de ingestelde voorkeursinstellingen terug kunnen ingevoerd worden via een policy refresh)

---

###UITVOERINGSVOLGORDE VAN GPO’S
- Letterwoord: LSDOU (Local - Site - Domain - OU)
- Indien verschillende GPO’s aan een container gekoppeld zijn, dan worden ze toegepast volgens dalend link order nr: de GPO met het laagste nr wordt laatst uitgevoerd en heeft dus de hoogste prioriteit
- Last Writer Wins: standaard vervangt een later toegepaste instelling een eerder toegepaste instelling wanneer de twee soorten instellingen conflicteren

---

###Er bestaan 2 methodes om dit verder te verfijnen:
- Security filtering: om aan te duiden welke gebruikers, computers en/of groepen de instellingen in een GPO ontvangen en toepassen
- WMI-filtering: (WMI = Windows Management Instrumentation) om te filteren op basis van de hardwareconfiguratie (bv: om een GPO enkel toe te passen op Windows 8 computers)

---

###Er bestaan 2 methodes om RD-sessies met een (of meer) server(s) te configureren:
- Via GPO’s: gebruik dit enkel voor RD-sessies met servers die geen RD Session Host servers zijn
- Via zgn “session collections”: gebruik dit steeds voor RD-sessies met servers die wel RD Session Host servers zijn

---

###Instellingen die op scopeniveau van DHCP geregeld worden:
- Address Pool: lijst met beschikbare IP-adressen
- Reservaties: IP-adressen kunnen op basis van MAC-adressen gereserveerd worden
- Options: IP-adressen van oa de default gateway en de DNS-servers kunnen samen met de IP-adressen van de clients meegegeven worden
- Policies: om uitdelen van IP-adressen te beperken

---

###Microsoft voorziet in 2 methodes voor een begeleide installatie van extra DHCP-servers:
- DHCP Split-Scope
    - Lease info wordt niet gerepliceerd tussen de DHCP-servers
    - Werken met dezelfde scopes, maar met andere “exclusions”
- DHCP Failover (nieuw in Windows Server 2012)
    - Lease info wordt gerepliceerd tussen de DHCP-servers
    - Werkt enkel met IPv4
    - Kan in 2 modes werken:
        - Hot standby: werkt met een primaire en een secundaire server
        - Load sharing: werkt met load balancing
		
---

###Tijdens installatie DNS-server keuze uit 3 zonetypes:
- Standard primary zone: de zone database wordt in een tekstfile op de server bewaard
- Standard secondary zone: werkt met een kopie van de zone database file die vanaf de primary DNS-server gerepliceerd wordt volgens een door de administrator opgegeven replicatieschema
    - Replicatie is afh vd inhoud vh serienr in de SOA-record
- Active Directory integrated zone: de zone database wordt in de A.D. bewaard (en dus niet als apart tekstbestand)
    - Beste keuze voor een DNS-server op een DC (automatische replicatie)
    - Kan enkel op een DC
	
---

###Op een DNS-server kunnen er 2 soorten zones gedefinieerd worden:
- Forward lookup zone
    - Standaard geïnstalleerd
    - Gebruikt voor forward lookup query’s: “Welk IP-adres hoort bij de host met naam….?”
    - Maakt gebruik van A-records
- Reverse lookup zone
    - Standaard NIET geïnstalleerd
    - Gebruikt voor reverse lookup query’s: “Welke hostnaamhoort bij het IP-adres…?”
    - Maakt gebruik van PTR-records
	
---

###Belangrijkste soorten resource records
- Start of Authority (SOA)
    - Bevat naam vd primary DNS-server voor de zone
    - Bevat serienr dat belangrijk is bij zone replication
- Name Server (NS): bevat IP-adressen van alle DNS-servers die bevoegdheid hebben voor de zone
- Host (A): koppelt een IP-adres aan een hostname; gebruikt bij forward lookup query’s
- POINTER (PTR): koppelt een hostname aan een IP-adres; gebruikt bij reverse lookup query’s
- Alias (CNAME): alternatieve naam voor een host
- Mail Exchanger (MX): bevat de naam vd server die e-mail voor dat domein accepteert

---

###Een Windows-server bevat een aantal standaard DNSsecurity instellingen:
- Bescherming tegen “DNS cache pollution/poisoning (DNS Spoofing)”: zorgt ervoor dat antwoorden op DNS-query’s enkel van “authoritive servers” aanvaard worden en in de DNS-cache opgeslagen worden -> bescherming tegen een DNS-redirection attack
- Cache locking: zorgt ervoor dat de DNS server niet toelaat dat cached records overschreven worden, zolang ze in de cache zitten (ingesteld door de TTL-waarde): het) cachelockingpercentage staat standard ingesteld op 100%

---

###Er zijn 2 policies voor logon auditing:
- “Audit account logon events”: voor accounts die zich aanmelden op een domein -> in logboek v/d DC
- “Audit logon events”
    - Voor accounts die zich lokaal aanmelden -> in logboek v/d lokale computer
    - Voor accounts die een share benaderen via het netwerk -> in logboek v/d server met de share
	
---

###Hoe deze audit info beschermen?
- Logs archiveren én deze archiefbestanden auditen
- Logs backuppen
- Logs op een andere (centrale) server bewaren
- Logs op read only media bewaren
- Logs beveiligen mbv NTFS-permissions

---

###Een printer kan op verschillende manieren in een netwerk opgenomen worden:
- De printer is verbonden met een server (via parallelle poort, USB,…)
- De printer is aangesloten op een printserver (dedicated hardware) met daarop een aantal parallelle of USB-poorten (verouderd)
- De printer is rechtstreeks (via UTP-kabel en ingebouwde NIC) aangesloten op het netwerk (meest toegepast)

---

###De beveiliging van een printer kan op 2 manieren via de printmanagementconsole geregeld worden:
- Op printserverniveau
    - gebeurt via het eigenschappenblad van de printserver
    - beveiligingsinstellingen zijn van toepassing op alle printers die daarna op die printserver geïnstalleerd worden
- Op printerniveau
    - gebeurt via het eigenschappenblad van de printer
    - beveiligingsinstellingen zijn enkel op die printer van toepassing

---

###Evolutie printerdrivers in Windows Server
- NT4: printerdrivers = kernel drivers -> bug in driver kon een volledige system crash veroorzaken
- Windows 2000: user mode printerdrivers  -> bug in driver kon enkel een crash van de printspooler veroorzaken
- Windows 2008R2: printerdriver isolation -> printspooler en evt ook andere drivers crashen niet bij een bug in een printerdriver

---

###Printer driver isolation modes:
- None: drivers worden geladen in het printspooler subsysteem;  komt overeen met oudere Windows-versies
- Shared (standaardinstelling): drivers worden geladen in een apart proces dat gescheiden is van de printspooler. Printer-drivers kunnen elkaar onderling nog wel dwars zitten
- Isolated: elke driver wordt gestart in een eigen proces. In dit geval worden ook de andere printerdrivers beschermd.

---

###In Windows-netwerken worden er 2 soorten user authentication protocollen gebruikt:
- NTLM : gebruikt in het werkgroepmodel
- Kerberos: standaard gebruikt in het domeinmodel

---

###Een access token bevat o.m.
- het SID van de gebruiker
- de SID’s van de groepen waartoe hij behoort 
- de Windows-privileges (user rights) van de gebruiker en de groepen waartoe hij behoort op de computer waarop hij zich aanmeldt (zie verder)

---

###Er bestaan 2 soorten access tokens:
- een standard user access token dat gebruikt wordt om gewone gebruikerstaken uit te voeren (vb: Windows Explorer openen)
- een administrator access token dat gebruikt wordt om administratortaken te kunnen uitvoeren (vb: een gebruikersaccount aanmaken)

----

###Mbv UAC kan je aanduiden welk waarschuwingsniveau er moet ingesteld  worden:
- Altijd waarschuwen (veiligst, maar veel prompts)
- Alleen een melding weergeven wanneer programma's proberen wijzigingen aan te brengen (bureaublad dimmen) (dit is de standaardinstelling)
- Alleen een melding weergeven wanneer programma's proberen wijzigingen aan te brengen (bureaublad niet dimmen)
- Nooit waarschuwen (minst veilig; af te raden)

---

###Er bestaan 2 methodes om het gebruik van applicaties (op clients) voor gebruikers te beperken:
- Software Restriction Policies (SRP’s): bestaat sinds Windows Server 2003
- AppLocker policies: nieuw vanaf Windows Server 2008R2

---

###3 soorten security levels in SRP's:
- Disallowed: om alle software te blokkeren
- Basic User: om enkel software toe te laten waarvoor geen speciale machtigingen nodig zijn
- Unrestricted (standaard instelling): om alle software toe te laten (als de gebruiker hiervoor over de nodige machtigingen beschikt)

---

###De identificatie van zo’n uitzondering gebeurt op basis van:
- Path: kan zowel het pad naar een folder of een programma zijn
- Network Zone: identificeert één van de zones in IE (Internet, Local Computer, Local Intranet, Restricted Sites en Trusted Sites)
- Certificate: enkel mogelijk als de applicatie digitaal ondertekend is mbv een certificaat
- Hash: vooraleer een applicatie opgestart wordt, wordt een hash berekend en vergeleken met deze van de uitzondering

---

###Verschillen tussen Applocker en SRP’s:
- Applocker ondersteunt “audit mode” om het effect van een policy uit te testen, vooraleer dit effectief toegepast wordt
- Applocker ondersteunt importeren en exporteren van policies
- Applocker kan applicaties ook blokkeren op basis van een digitale handtekening van de ontwikkelaar. Hierdoor kan je bv. applicaties blokkeren op basis van een versienummer…

---

###AppLocker-regels kunnen op 4 soorten bestanden toegepast worden:
- Uitvoerbare bestanden (.EXE en .COM) -> executable rules
- Scripts (.JS, .PS1, .VBS, .CMD en .BAT) -> script rules
- Windows Installer-bestanden (.MSI en .MSP) -> Windows Installer rules
- Windows apps -> Packaged app rules

---

###3 criteria waarop een AppLocker-regel kan gebaseerd zijn:
- Publisher: hierbij wordt een applicatie herkend op basis v/d bijhorende digitale handtekening (indien deze aanwezig is) en uitgebreide kenmerken zoals de naam en het versienummer v/d applicatie
- Path: hierbij wordt een applicatie herkend op basis van zijn locatie in het bestandssysteem
- File hash: hierbij wordt applicatie herkend op basis van een berekende cryptografische hash-functie

---

###3 handhavingsmodi
- Niet geconfigureerd: Dit is de standaardinstelling. Als gekoppelde groepsbeleidobjecten een andere instelling bevatten, wordt die instelling gebruikt.
- Regels handhaven: Regels worden gehandhaafd.
- Alleen controle: Regels worden gecontroleerd, maar niet gehandhaafd. Soort van test-modus.

---

###WSB biedt onderstaande backupmogelijkheden:
- Backup van een aantal files en/of folders en/of volumes
- System state backup: in dit geval wordt enkel de systeemstatus gebackupt. De systeemstatus bestaat o.a. uit het register, opstartbestanden, en nog een aantal items die afhankelijk zijn van welke rollen er op de server geïnstalleerd  zijn. Zo bevat de systeemstatus van een DC o.m. de Active Directory en de SYSVOL-folder.
- Full server backup: in dit geval worden alle schijven én de systeemstatus gebackupt

---

###Alternatieven voor Active Directory recovery:
- Via de Recycle Bin van de Active Directory (indien geactiveerd)
- Mbv Active Directory snapshots. Hierbij dient gebruikgemaakt te worden van het commando ntdsutil

---

###Vereisten voor WDS:
- Installatie op een server die lid is van een domein
- Aanwezigheid van DHCP-server en DNS-server in netwerk
- De server waarop WDS wordt geïnstalleerd moet een NFTS- partitie hebben waarop de boot images worden geplaatst

---

###Andere bestandsformaten die door de Windows Installer kunnen gebruikt worden:
- MSP (Microsoft Installer Patch): wordt gebruikt om patches aan te brengen op reeds eerder geïnstalleerde programma's.
- MST (Microsoft Installer Transform): wordt gebruikt om aanpassingen aan te brengen aan de oorpronkelijke MSI.

---

###Bij software deployment kan je kiezen uit:
- Software assigning (toewijzing): kan zowel op gebruikers als computerniveau
- Software publishing: kan enkel op gebruikersniveau

---

###3 windows activatiemethodes:
- Retail: in dit geval dien je ter activatie een product key in te voeren binnen een bepaalde periode na de installatie ingevoerd worden (grace period)
- OEM: in dit geval werd het voorgeïnstalleerde product voorzien van een speciale product key van de Original Equipment Manufacturer
- Volume: in dit geval wordt er gebruikgemaakt van één enkele product key voor verschillende installaties, hetgeen zeer interessant is voor de bedrijfswereld; men spreekt in dit geval van een VLK (Volume Licence Key)

---

###Microsoft voorziet 3 soorten volume licentie-systemen:
- MAK = Multiple Activation Keys: voor een beperkt aantal activaties
- KMS = Key Management Service: voor een groot aantal activaties
- Active Directory Based Activation: voor een groot aantal activaties (vanaf Windows 8/Server 2012)

----

###Bij WSUS kan er uit 2 soorten managementmethodes gekozen worden: 
- Centralized management: maakt gebruik van replica servers die centraal beheerd worden en dus enkel voor distributie van groepen en updates gebruikt worden.
- Distributed management: maakt gebruik van autonome servers die apart beheerd worden (meestal door lokale administrators).

---

###Bij gebruik van DAC kan de toegang tot files en folders ook geregeld worden op basis van:
- “claims” (aanspraken)
- resource properties
- central access policies (CAP’s)

---

###Een claim is een bewering ivm een AD-attribuut van een…
- gebruiker -> user claim (bv: een gebruiker van het HR-departement)
- computer -> device claim (bv: een Windows 8.1 computer)

---

