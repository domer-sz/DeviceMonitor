# README #
Aplikacja została napisana według uproszczoneg wzorca CQRS.

Aplikacja napisana dla firmy Eteko s.c. do agregowania danych i analizy stanu sterowników PLC zarządzających dowolnymi urządzeniami. W czasie rzeczywistym analizuje dane przychodzące z sterowników i na ich podstawie lub ich braku określa stan urządzeń, przerwy w działaniu, zgłoszenia awarii, przeglądów. Stan urządzeń oraz ich lokalizacja są wyświetlane na mapie i za pomocą websocketów dynamicznie zmienianie gdy stan się zmieni. Aplikacja pozwala również na przeglądanie danych historycznych w formie wykresów, raportów danych oraz wysyłanie komend sterujących do urządzeń. Poszczególne funkcjonalności przypisane są do trzech poziomów ról użytkowników.
Technologie: Spring Boot, Spring MVC, Spring Data, Spring Security, WebSockets, JMS, MySql

Aplikacja do uruchomienia potrzebuje bazy MySQL o nazwie device_monitor. 
Dump takiej bazy znajduje się w folderzę databaseStart.
