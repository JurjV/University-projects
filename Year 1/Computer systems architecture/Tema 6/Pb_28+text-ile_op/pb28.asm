;A file name (defined in data segment) is given. Create a file with the given name, then read words from the keyboard until character '$' is read. Write only the words that contain at least one lowercase letter to file.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fopen,scanf,fclose,fprintf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fprintf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    file_name db "file.txt",0
    write_mode db "w",0
    file_descriptor dd -1
    cuvant times 100 db 0
    cuvant_format db "%s",0
    cuv_de_scris times 100 db 0 
    cuv_de_scris_format db "%s",0ah,0

; our code starts here
segment code use32 class=code
    start:
        push write_mode
        push file_name
        call [fopen]  ; deschidem fisierul
        add esp,4*2
        
        mov [file_descriptor],eax
        cmp eax,0
        je Final  ; daca avem eroare la crearea si deschiderea fisierului
        
        Reading_loop:
        push cuvant
        push cuvant_format
        call [scanf]  ;citim un cuvant de la tastatura
        add esp,4*2
        
        mov esi,cuvant
        mov edi,cuv_de_scris
        mov dl,0
        Prelucrare_cuvant:
            lodsb 
            cmp al,"$"  ; conditia de a termina de citit
            je Gata
            
            cmp al,0
            je Urmatorul_cuvant
            cmp al,"a"
            jb Not_valid
            cmp al,"z"
            ja Not_valid
            inc dl   ; creste daca gasim litera mica in cuvant
            Not_valid:
            stosb
            jmp Prelucrare_cuvant
            
            Urmatorul_cuvant: ;adaugam cuvantul in text file si trecem la urmatorul
                cmp dl,0
                je Nu_adaug
                push dword cuv_de_scris
                push dword cuv_de_scris_format
                push dword [file_descriptor]
                call [fprintf]
                add esp,4*3
                Nu_adaug:
                jmp Reading_loop
            Gata:
            
        push dword [file_descriptor]
        call [fclose]
        add esp,4
        
        Final:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
