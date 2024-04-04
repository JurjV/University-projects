;A text file is given. Read the content of the file, count the number of vowels and display the result on the screen. The name of text file is defined in the data segment.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf,fread,fopen,fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fread msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    file_name db "file.txt",0
    read_mode db "r",0
    file_descriptor dd -1
    characters_read dd 0
    len equ 100
    text resb 2000
    search_characters db "aeiouAEIOU",0
    char_start equ $ - search_characters
    nr_of_vowels dd 0
    write_format db "There were %d vowels in the file",0

    

; our code starts here
segment code use32 class=code
    start:
        push dword read_mode
        push dword file_name
        call [fopen]
        add esp,4*2
        
        cmp eax,0
        je Final
        mov [file_descriptor],eax
        Program:
            push dword [file_descriptor]
            push dword len
            push dword 1
            push dword text
            call [fread]
            add esp,4*4
            
            add [characters_read],eax
            cmp eax,0
            jne Program
            
        mov ecx,[characters_read]
        mov esi,text
        While_loop:
            push ecx
            mov ecx,char_start
            lodsb 
            mov edi,search_characters
            Check:
                scasb
                jne Not_this_vowel
                add dword [nr_of_vowels],1
                jmp Found_a_vowel
                Not_this_vowel:
                loop Check
            Found_a_vowel:
            pop ecx
            loop While_loop
        Final:
        push dword [file_descriptor]
        call [fclose]
        add esp,4
        
        push dword [nr_of_vowels]
        push dword write_format
        call [printf]
        add esp,4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
