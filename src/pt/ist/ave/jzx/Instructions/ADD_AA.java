package pt.ist.ave.jzx.Instructions;

public class ADD_AA extends Instruction {

	public ADD_AA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		//add_a(m_a8);
		_cpu.add_a(_cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		//m_tstates += 4;
		return 4;
	}

}
