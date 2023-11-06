import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('FieldKpi e2e test', () => {
  const fieldKpiPageUrl = '/field-kpi';
  const fieldKpiPageUrlPattern = new RegExp('/field-kpi(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const fieldKpiSample = {};

  let fieldKpi;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/field-kpis+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/field-kpis').as('postEntityRequest');
    cy.intercept('DELETE', '/api/field-kpis/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (fieldKpi) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/field-kpis/${fieldKpi.id}`,
      }).then(() => {
        fieldKpi = undefined;
      });
    }
  });

  it('FieldKpis menu should load FieldKpis page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('field-kpi');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FieldKpi').should('exist');
    cy.url().should('match', fieldKpiPageUrlPattern);
  });

  describe('FieldKpi page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(fieldKpiPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FieldKpi page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/field-kpi/new$'));
        cy.getEntityCreateUpdateHeading('FieldKpi');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldKpiPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/field-kpis',
          body: fieldKpiSample,
        }).then(({ body }) => {
          fieldKpi = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/field-kpis+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/field-kpis?page=0&size=20>; rel="last",<http://localhost/api/field-kpis?page=0&size=20>; rel="first"',
              },
              body: [fieldKpi],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(fieldKpiPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FieldKpi page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('fieldKpi');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldKpiPageUrlPattern);
      });

      it('edit button click should load edit FieldKpi page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldKpi');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldKpiPageUrlPattern);
      });

      it('edit button click should load edit FieldKpi page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldKpi');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldKpiPageUrlPattern);
      });

      it('last delete button click should delete instance of FieldKpi', () => {
        cy.intercept('GET', '/api/field-kpis/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('fieldKpi').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldKpiPageUrlPattern);

        fieldKpi = undefined;
      });
    });
  });

  describe('new FieldKpi page', () => {
    beforeEach(() => {
      cy.visit(`${fieldKpiPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FieldKpi');
    });

    it('should create an instance of FieldKpi', () => {
      cy.get(`[data-cy="kpis"]`).type('core optical Mountains').should('have.value', 'core optical Mountains');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        fieldKpi = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', fieldKpiPageUrlPattern);
    });
  });
});
