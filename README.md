# infoh400

The objective of this project is to simulate a Hospital Information System (HIS). To do so, 4 main classes are used (Patient, Doctor, Appointment and Image).

The HIS is connected to a PACS that allows to store images from a DICOMDIR to the disk. The images stored in the PACS can also be extracted and displayed.

Finally, an HL7 communication protocol is added to simulate the exchange of ADT A01 messages between different departments and thus create a new patient in the database if not already existing.
