;A text file is given. Read the content of the file, count the number of consonants and display the result on the screen. The name of text file is defined in the data segment.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fread,fclose,fopen,printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fread msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    num_of_consonants dd 0
    file_descriptor dd 0
    file_name db "file.txt",0
    read_mode db "r",0
    text resb 2000
    characters_read dd 0
    compare_characters db "aeiouAEIOU",0
    vowel_str equ $- compare_characters
    print_format db "Nr. of consonants in file is %d.",0

; our code starts here
segment code use32 class=code
    start:
        push dword read_mode
        push dword file_name
        call [fopen]
        add esp,4*2
        
        cmp eax,0
        je Gata
        mov [file_descriptor],eax
        Reading_loop:
            push dword [file_descriptor]
            push dword 100
            push dword 1
            push dword text
            call [fread]
            add esp,4*4
            
            add [characters_read],eax
            cmp eax,0
            jne Reading_loop
        
        mov ecx,[characters_read]
        mov esi,text
        Compare_characters:
            push ecx
            mov ecx,vowel_str
            lodsb
            mov edi,compare_characters
            compare:
                scasb
                je Stop
                loop compare
            Verify_if_character:
                cmp Al,"A"
                jb Stop
                cmp AL,"z"
                ja Stop
                cmp AL,"Z"
                jbe Is_character
                cmp AL,"a"
                jae Is_character
                jmp Stop
                Is_character:
            add dword [num_of_consonants],1
            Stop:
            pop ecx
            loop Compare_characters
        
        push dword [num_of_consonants]
        push dword print_format
        call [printf]
        add esp,4*2
                
            
        
        Gata:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
