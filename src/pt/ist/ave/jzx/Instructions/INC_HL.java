package pt.ist.ave.jzx.Instructions;

public class INC_HL extends Instruction {

	public INC_HL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.inc16hl();
	}

	@Override
	public int incTstates() {
		return 6;
	}

}
