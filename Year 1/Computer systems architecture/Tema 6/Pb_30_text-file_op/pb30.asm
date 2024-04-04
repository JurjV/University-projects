;A file name (defined in data segment) is given. Create a file with the given name, then read words from the keyboard until character '$' is read from the keyboard. Write only the words that contain at least one digit to file.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fopen,fclose,scanf,fprintf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fprintf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    file_name db "file.txt",0
    file_type db "w",0
    file_descriptor dd -1
    cuvant times 100 db 0
    cuvant_type db "%s",0
    cuvant_prelucrat times 100 db 0
    cuvant_de_scris_format db "%s",0ah,0

; our code starts here
segment code use32 class=code
    start:
        push dword file_type
        push dword file_name
        call [fopen]
        add esp,4*2
        
        mov [file_descriptor],eax
        cmp eax,0
        je Final
        
        Read_loop:
        push dword cuvant
        push dword cuvant_type
        call [scanf]
        add esp,4*2
        
        mov esi, cuvant
        mov edi, cuvant_prelucrat
        mov dl,0
        cld
        Prelucrare_cuvant:
            lodsb
            cmp al,"$"
            jne Peste
            jmp Gata
            Peste:
            
            cmp al,0
            je Urmatorul_cuvant
            
            cmp al,"0"
            jb Not_good
            cmp al,"9"
            ja Not_good
            inc dl
            Not_good:
            stosb
            jmp Prelucrare_cuvant
            
            Urmatorul_cuvant:
                cmp dl,0
                je Mai_departe
                push dword cuvant_prelucrat
                push dword cuvant_de_scris_format
                push dword [file_descriptor]
                call [fprintf]
                add esp,4*3
            
                Mai_departe:
                jmp Read_loop
        
        Gata:
        
        push dword [file_descriptor]
        call [fclose]
        add esp,4
        
        Final:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
