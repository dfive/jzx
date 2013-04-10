package pt.ist.ave.jzx.Instructions;

public class POP_HL extends Instruction {

	public POP_HL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		//hl16(pop16());
		_cpu.hl16(_cpu.pop16());
	}

	@Override
	public int incTstates() {
		return 10;
	}

}
